<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<!-- App.vue -->

<template>
  <div>
    <Header />
    <HomePage /> <!-- Use the new HomePage component -->
    <Search />
    <router-view /> <!-- This is where other components will be displayed based on the route -->
  </div>
</template>

<script>
import Header from './components/Header.vue';
import Search from './components/Search.vue';

import HomePage from './components/HomePage.vue'; // Import the new HomePage component

import 'bootstrap/dist/css/bootstrap.min.css';

export default {
  components: {
    Header,
    HomePage,
    Search,
  }
}
</script>

<style>

</style>
