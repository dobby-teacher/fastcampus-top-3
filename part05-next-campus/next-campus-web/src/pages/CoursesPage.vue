<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1>Courses</h1>
      </v-col>
      <v-col cols="12" md="4" v-for="course in courses" :key="course.id">
        <v-card>
          <v-card-title>{{ course.title }}</v-card-title>
          <v-card-text>{{ course.description }}</v-card-text>
          <v-card-actions>
            <router-link :to="`/purchase/${course.id}`">
              <v-btn color="primary">Purchase Course</v-btn>
            </router-link>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {useQuery} from '@vue/apollo-composable';
import gql from 'graphql-tag';

const LIST_COURSES = gql`
  query ListCourses {
    listCourses {
      id
      title
      description
    }
  }
`;

export default {
  name: 'CoursesPage',
  setup() {
    const {result, loading, error} = useQuery(LIST_COURSES);
    return {
      courses: result,
      loading,
      error,
    };
  },
};
</script>
