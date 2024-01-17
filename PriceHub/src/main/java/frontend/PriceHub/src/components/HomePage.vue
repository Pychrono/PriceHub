<!-- HomePage.vue -->

<template>
  <nav>
    <!-- Heading and Introduction Section -->
    <div class="main-div">
        <div class="text">
          <h2>Made for the Season</h2>

          <div class="first">
            <p>Introducing Inside Out.</p>
          </div>
          <div class="second">
            <p>Our latest collection.</p>
          </div>
          <button>SHOP</button>
        </div>
        <div class="img col-6">
          <img src="/yu.jpg" />
        </div>
        <!-- <div class="img">
        </div> -->
      </div>

    <!-- Watches Gallery Section -->
        <div class="container mt-5">
          <div class="row">
            <!-- Loop through filtered watches and display in small boxes -->
            <div v-for="watch in filteredWatches" :key="watch.id" class="col-md-4 mb-4">
              <div class="card">
                <div class="watch-card">
                    <img :src="watch.image_url" alt="Watch Image" class="card-img-top img-fluid" />
                    <div class="card-body">
                      <h5 class="card-title">{{ watch.name }}</h5>
                      <p class="card-text">Price: {{ watch.price }}</p>
                      <!-- Add more details as needed -->
                    </div>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </template>

<script>
import WatchCard from './WatchCard.vue';

export default {
  data() {
    return {
      searchQuery: '',
      watches: [], // Assuming you have a watches array from your API
    };
  },
  created() {
    // Fetch the first 30 watches when the component is created
    this.fetchWatches(30);
  },
  computed: {
    // Compute filteredWatches based on the searchQuery
    filteredWatches() {
      return this.watches.filter((watch) =>
        watch.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchWatches(limit) {
      try {
        const response = await fetch(`http://localhost:3000/api/watches?limit=${limit}`);

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
  components: {
    WatchCard,
  },
};
</script>

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

<style scoped>
/* Your component-specific styles go here */

.main-div {
  display: flex;
  justify-content: space-between;
  height: 60vh;
  width: 100%;
}
.text {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  padding-bottom: 4rem;
  /* padding-left: 4rem; */
}
img {
  height: 100%;
  width: auto;
  margin-top: 2em;
  /* padding-right: 2rem; */
}
h2 {
  font-size: 24px;
  display: flex;
  justify-content: center;
  /* margin-top: 10px; */
  margin-left: 40px;
}
p {
  margin-top: 12px;
  line-height: 15px;
}
butt
button {
  background-color: black;
  border: none;
  color: white;
  padding: 10px 16px;
  margin-left: 80px;
  margin-right: 50px;
  margin-top: 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  display: flex;
  justify-content: center;
  /* margin-top: 10px; */
  /* margin-left: 40px; */
}
.first {
  display: flex;
  justify-content: center;
  /* margin-top: 10px; */
  margin-left: 40px;
}
.second {
  display: flex;
  justify-content: center;
}

/* Style for the watches gallery */
.watches-gallery {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  margin: 0 auto;
}

header h1
{
    margin-top: 100px;
}
.watch-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  width: 100%; /* Adjust width as needed */
}


</style>
