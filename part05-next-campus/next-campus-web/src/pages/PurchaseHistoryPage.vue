<template>
  <v-container>
    <h1>Purchase History</h1>
    <v-alert v-if="loadingEnrollments || loadingSubscriptions" type="info">Loading...</v-alert>
    <v-alert v-if="errorEnrollments" type="error">Error loading enrollments: {{ errorEnrollments.message }}</v-alert>
    <v-alert v-if="errorSubscriptions" type="error">Error loading subscriptions: {{ errorSubscriptions.message }}</v-alert>

    <h2>Enrollments</h2>
    <v-list v-if="enrollments">
      <v-list-item v-for="enrollment in enrollments" :key="enrollment.id">
        <v-list-item-content>
          <v-list-item-title>{{ enrollment.course.title }}</v-list-item-title>
          <v-list-item-subtitle>Amount: {{ enrollment.payment.amount }}</v-list-item-subtitle>
          <v-list-item-subtitle>Date: {{ enrollment.payment.paymentDate }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <h2>Subscriptions</h2>
    <v-list v-if="subscriptions">
      <v-list-item v-for="subscription in subscriptions" :key="subscription.id">
        <v-list-item-content>
          <v-list-item-title>Subscription</v-list-item-title>
          <v-list-item-subtitle>Amount: {{ subscription.payment.amount }}</v-list-item-subtitle>
          <v-list-item-subtitle>Start Date: {{ subscription.startDate }}</v-list-item-subtitle>
          <v-list-item-subtitle>End Date: {{ subscription.endDate }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-container>
</template>

<script>
import { useQuery } from '@vue/apollo-composable';
import gql from 'graphql-tag';
import {ref, watchEffect} from 'vue';

const GET_USER_ENROLLMENTS = gql`
  query GetUserEnrollments($userId: ID!) {
    getUserEnrollments(userId: $userId) {
      id
      course {
        title
      }
      payment {
        amount
        paymentDate
      }
    }
  }
`;

const GET_USER_PLAN_SUBSCRIPTIONS = gql`
  query GetUserPlanSubscriptions($userId: ID!) {
    getUserPlanSubscriptions(userId: $userId) {
      id
      payment {
        amount
        paymentDate
      }
      startDate
      endDate
    }
  }
`;

export default {
  name: 'PurchaseHistoryPage',
  setup() {
    const userId = ref(localStorage.getItem('userId'));

    const { result: enrollmentsResult, loading: loadingEnrollments, error: errorEnrollments } = useQuery(GET_USER_ENROLLMENTS, {
      userId: userId.value
    });

    const { result: subscriptionsResult, loading: loadingSubscriptions, error: errorSubscriptions } = useQuery(GET_USER_PLAN_SUBSCRIPTIONS, {
      userId: userId.value
    });

    const enrollments = ref([]);
    const subscriptions = ref([]);

    watchEffect(() => {
      if (enrollmentsResult.value) {
        enrollments.value = enrollmentsResult.value.getUserEnrollments;
      }
    });

    watchEffect(() => {
      if (subscriptionsResult.value) {
        subscriptions.value = subscriptionsResult.value.getUserPlanSubscriptions;
      }
    });

    return {
      enrollments,
      subscriptions,
      loadingEnrollments,
      loadingSubscriptions,
      errorEnrollments,
      errorSubscriptions,
    };
  },
};
</script>

<style>
/* 필요에 따라 스타일 추가 */
</style>
