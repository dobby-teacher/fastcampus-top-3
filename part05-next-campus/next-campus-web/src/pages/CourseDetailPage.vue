<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="12">
        <v-card>
          <v-card-title>{{ course?.title }}</v-card-title>
          <v-card-subtitle>{{ course?.description }}</v-card-subtitle>
          <v-card-text>
            <v-alert v-if="loading" type="info">Loading...</v-alert>
            <v-alert v-if="error" type="error">Error loading course details: {{ error.message }}</v-alert>
            <div v-if="course">
              <v-alert v-if="!hasAccess" type="warning">You do not have access to this course. Please purchase to gain access.</v-alert>

              <v-row>
                <v-col cols="12" md="4">
                  <h3>Sessions</h3>
                  <v-list>
                    <v-list-item
                        v-for="session in course.courseSessions"
                        :key="session.id"
                        @click="selectSession(session)"
                        :active="session.id === selectedSession?.id"
                    >
                      <v-list-item-content>
                        <v-list-item-title>{{ session.title }}</v-list-item-title>
                      </v-list-item-content>
                      <v-chip v-if="hasAccess" color="green" text-color="white">Purchased</v-chip>
                    </v-list-item>
                  </v-list>
                </v-col>

                <v-col cols="12" md="8" v-if="selectedSession && hasAccess">
                  <h3>Session Video</h3>
                  <v-card v-if="selectedSession.files.length > 0">
                    <v-card-text>
                      <video
                          v-if="selectedSession.files[0].fileType === 'mp4'"
                          controls
                          :src="`http://localhost:9000/files/sessions/${selectedSession.id}/streams`"
                          style="width: 100%;"
                      ></video>
                      <v-alert v-else type="info">No video available for this session.</v-alert>
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>

              <ChatComponent :course-id="courseId"></ChatComponent>

              <h3>Ratings</h3>
              <v-list>
                <v-list-item v-for="rating in course.ratings" :key="rating.id">
                  <v-list-item-content>
                    <v-list-item-title>{{ rating.user.name }}</v-list-item-title>
                    <v-list-item-subtitle>
                      Rating: {{ rating.rating }} - {{ rating.comment }}
                    </v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { useQuery } from '@vue/apollo-composable';
import gql from 'graphql-tag';
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import apolloClient from '@/apollo';
import ChatComponent from "@/components/ChatComponent.vue";

const GET_COURSE_DETAILS = gql`
  query GetCourseDetails($userId: ID!, $courseId: ID!) {
    getCourse(userId: $userId, courseId: $courseId) {
      id
      title
      description
      courseSessions {
        id
        title
        files {
          fileId
          fileName
          fileType
          filePath
        }
      }
      ratings {
        id
        rating
        comment
        user {
          name
        }
      }
    }
  }
`;

const CHECK_COURSE_ACCESS = gql`
  query CheckCourseAccess($userId: ID!, $courseId: ID!) {
    checkCourseAccess(userId: $userId, courseId: $courseId)
  }
`;

export default {
  name: 'CourseDetailPage',
  components: {ChatComponent},
  setup() {
    const route = useRoute();
    const userId = ref(localStorage.getItem('userId'));
    const courseId = ref(route.params.id);
    const hasAccess = ref(false);
    const selectedSession = ref(null);

    const { result, loading, error } = useQuery(GET_COURSE_DETAILS, {
        userId: userId.value,
        courseId: courseId.value,
    });

    const course = computed(() => result.value?.getCourse);

    // Check course access on component mount
    const checkAccess = async () => {
      try {
        const { data } = await apolloClient.query({
          query: CHECK_COURSE_ACCESS,
          variables: {
            userId: userId.value,
            courseId: courseId.value,
          },
        });
        hasAccess.value = data.checkCourseAccess;
      } catch (e) {
        console.error(e);
      }
    };

    checkAccess();

    const selectSession = (session) => {
      if (hasAccess.value) {
        selectedSession.value = session;
      } else {
        alert('You do not have access to this session.');
      }
    };

    return {
      course,
      loading,
      error,
      hasAccess,
      selectedSession,
      selectSession,
      courseId
    };
  },
};
</script>

<style>
/* 필요에 따라 스타일 추가 */
</style>
