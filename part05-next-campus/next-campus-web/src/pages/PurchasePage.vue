<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>Purchase Course</v-card-title>
          <v-card-text v-if="loading">Loading...</v-card-text>
          <v-card-text v-else>
            <div v-if="course">
              <h2>{{ course.title }}</h2>
              <p>{{ course.description }}</p>
              <v-form @submit.prevent="purchaseCourse">
                <v-text-field v-model="paymentMethod" label="Payment Method (e.g., Credit Card)" required></v-text-field>
                <v-text-field v-model="amount" label="Amount" type="number" required></v-text-field>
                <v-btn type="submit" color="primary">Purchase</v-btn>
              </v-form>
            </div>
            <v-alert v-if="error" type="error">Error loading course details.</v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {useQuery, useMutation} from '@vue/apollo-composable';
import gql from 'graphql-tag';
import {ref} from 'vue';
import {useRoute} from 'vue-router';

const GET_COURSE = gql`
  query GetCourse($userId: ID!, $courseId: ID!) {
    getCourse(userId: $userId, courseId: $courseId) {
      id
      title
      description
    }
  }
`;

const PURCHASE_COURSE = gql`
  mutation PurchaseCourse($userId: ID!, $courseId: ID!, $amount: Float!, $paymentMethod: String!) {
    purchaseCourse(userId: $userId, courseId: $courseId, amount: $amount, paymentMethod: $paymentMethod) {
      id
      amount
      paymentMethod
      paymentDate
    }
  }
`;

export default {
  name: 'PurchasePage',
  setup() {
    const route = useRoute();
    const userId = ref(localStorage.getItem('userId'));
    const courseId = ref(route.params.id);
    const paymentMethod = ref('');
    const amount = ref(0);

    const {result, loading, error} = useQuery(GET_COURSE, {
      userId: userId.value,
      courseId: courseId.value,
    });

    const [purchaseCourseMutation] = useMutation(PURCHASE_COURSE);

    const purchaseCourse = async () => {
      try {
        await purchaseCourseMutation({
          variables: {
            userId: userId.value,
            courseId: courseId.value,
            amount: parseFloat(amount.value),
            paymentMethod: paymentMethod.value,
          },
        });
        alert('Course purchased successfully!');
      } catch (e) {
        console.error(e);
        alert('Error purchasing course.');
      }
    };

    return {
      course: result,
      loading,
      error,
      paymentMethod,
      amount,
      purchaseCourse,
    };
  },
};
</script>
