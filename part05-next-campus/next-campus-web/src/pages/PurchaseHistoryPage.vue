<template>
  <v-container>
    <h1>Purchase History</h1>
    <v-alert v-if="loading" type="info">Loading...</v-alert>
    <v-alert v-if="error" type="error">Error loading purchase history.</v-alert>
    <v-list v-if="purchaseHistory">
      <v-list-item v-for="purchase in purchaseHistory" :key="purchase.id">
        <v-list-item-content>
          <v-list-item-title>{{ purchase.course.title }}</v-list-item-title>
          <v-list-item-subtitle>Amount: {{ purchase.payment.amount }}</v-list-item-subtitle>
          <v-list-item-subtitle>Date: {{ purchase.payment.paymentDate }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-container>
</template>

<script>
import { useQuery } from '@vue/apollo-composable';
import gql from 'graphql-tag';
import { ref } from 'vue';

const GET_USER_PLAN_SUBSCRIPTIONS = gql`
  query GetUserPlanSubscriptions($userId: ID!) {
    getUserPlanSubscriptions(userId: $userId) {
      id
      payment {
        amount
        paymentDate
      }
    }
  }
`;

export default {
  name: 'PurchaseHistoryPage',
  setup() {
    const userId = ref(localStorage.getItem('userId'));
    const { result, loading, error } = useQuery(GET_USER_PLAN_SUBSCRIPTIONS, {
      userId: userId.value,
    });

    return {
      purchaseHistory: result,
      loading,
      error,
    };
  },
};
</script>
