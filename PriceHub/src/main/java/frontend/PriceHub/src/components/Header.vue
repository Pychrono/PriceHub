<!-- Header.vue -->

<template>

<main>
    <header>
        <nav class="navbar navbar-expand-md navbar-light fixed-top bg-light">
          <div class="container-fluid">
            <router-link class="navbar-brand" to="/">KingPriceHub</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
              aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
              <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <li class="nav-item">
                  <router-link class="nav-link" to="/">Home</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/about">About</router-link>
                </li>
              </ul>
              <div class="d-flex">
                <input v-model="searchQuery" placeholder="Search watches..." />
                <button @click="searchWatches">Search</button>
              </div>
            </div>
          </div>
        </nav>
      </header>
</main>

</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
    };
  },
  methods: {
    async searchWatches() {
      try {
        const response = await fetch(`http://localhost:3000/api/watches?search=${this.searchQuery}`);
        if (!response.ok) {
          throw new Error(`Failed to fetch watches. Status: ${response.status}`);
        }

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
          throw new Error('Invalid content type. Expected JSON.');
        }

        const data = await response.json();
        this.$emit('search-results', data); // Emit an event with search results
      } catch (error) {
        console.error('Error fetching watches:', error.message);
      }
    },
  },
};
</script>

<style scoped>
/* Header component-specific styles go here */
</style>
