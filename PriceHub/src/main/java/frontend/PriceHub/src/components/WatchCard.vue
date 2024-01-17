<!-- WatchCard.vue -->


<template>
  <div class="watch-card">
    <img :src="watch.image_url" alt="Watch Image" class="card-img-top" />
    <div class="card-body">
      <h5 class="card-title">{{ watch.name }}</h5>
      <p class="card-text">Price: {{ watch.price }}</p>
      <!-- Add more details as needed -->
    </div>
  </div>
</template>

<script>
export default {
  props: {
    watch: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
.watch-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
  width: 100%; /* Adjust width as needed */
}

img {
  width: 100%;
  height: auto; /* Adjust height as needed */
  object-fit: cover;
}

.card-body {
  padding: 10px;
}
</style>
