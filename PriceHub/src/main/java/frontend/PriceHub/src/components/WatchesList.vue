<!-- WatchesList.vue -->

<template>
  <div>
    <h2>Watches List</h2>
    <ul>
      <li v-for="watch in watches" :key="watch.id">
        <img :src="watch.image_url" alt="Watch Image" />
        <div>
          <h3>{{ watch.name }}</h3>
          <p>Price: {{ watch.price }}</p>
          <p>Website: {{ watch.website_url }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      watches: [],
    };
  },
 created() {
     // Fetch watches data when the component is created
     this.fetchWatches();
   },
   methods: {
     async fetchWatches() {
       try {
         const response = await fetch('http://localhost:3000/api/watches');

         if (!response.ok) {
           throw new Error(`Failed to fetch watches. Status: ${response.status}`);
         }

         const contentType = response.headers.get('content-type');
         if (!contentType || !contentType.includes('application/json')) {
           throw new Error('Invalid content type. Expected JSON.');
         }

         const data = await response.json();
         this.watches = data;
       } catch (error) {
         console.error('Error fetching watches:', error.message);
       }
     },
   },
 };
 </script>

<style scoped>
  /* Your component-specific styles go here */
</style>
