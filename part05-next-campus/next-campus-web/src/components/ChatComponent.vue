<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title>Chat</v-card-title>
          <v-card-text>
            <div v-if="loading" class="text-center">Loading messages...</div>
            <div v-else class="scroll-view">
              <div v-for="message in messages" :key="message.messageId" class="message">
                <strong>{{ message.userId }}:</strong> {{ message.content }}
              </div>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-text-field
                v-model="newMessage"
                label="Type your message"
                @keyup.enter="sendMessage"
            ></v-text-field>
            <v-btn @click="sendMessage">Send</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {useSubscription, useMutation, useQuery} from '@vue/apollo-composable';
import {ref, toRaw, watch} from 'vue';
import gql from "graphql-tag";

const GET_MESSAGES = gql`
  query GetMessages($courseId: String!) {
    getMessages(courseId: $courseId) {
      courseId
      userId
      messageId
      content
      timestamp
    }
  }
`;

const SEND_MESSAGE = gql`
  mutation SendMessage($courseId: String!, $userId: String!, $content: String!) {
    sendMessage(courseId: $courseId, userId: $userId, content: $content)
  }
`;

const MESSAGE_RECEIVED = gql`
  subscription MessageReceived($courseId: String!) {
    messageReceived(courseId: $courseId) {
      courseId
      userId
      messageId
      content
      timestamp
    }
  }
`;

export default {
  name: 'ChatComponent',
  props: {
    courseId: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const messages = ref([]);
    const newMessage = ref('');
    const userId = ref(localStorage.getItem('userId'));

    // Query to get existing messages
    const { result: queryResult, loading: queryLoading } = useQuery(GET_MESSAGES, {
      courseId: props.courseId,
    });
    //
    watch(queryResult, (newResult) => {
      if (newResult && newResult.getMessages) {
        messages.value = newResult.getMessages;
      }
    });

    // Subscription to receive new messages
    const { result } = useSubscription(MESSAGE_RECEIVED, {
      courseId: props.courseId,
    });

    watch(result, (newResult) => {
      if (newResult && newResult.messageReceived) {
        messages.value = [...toRaw(messages.value), newResult.messageReceived];
      }
    });

    // Mutation to send a new message
    const { mutate: sendMessageMutation } = useMutation(SEND_MESSAGE);

    const sendMessage = () => {
      if (newMessage.value.trim()) {
        sendMessageMutation({
          courseId: "100",//props.courseId,
          userId: userId.value,
          content: newMessage.value,
        });
        newMessage.value = '';
      }
    };

    return {
      messages,
      newMessage,
      sendMessage,
      queryLoading
    };
  },
};
</script>

<style>
.message {
  margin-bottom: 10px;
}

.scroll-view {
  max-height: 400px; /* 스크롤 영역의 최대 높이를 설정합니다 */
  overflow-y: auto;
}
</style>
