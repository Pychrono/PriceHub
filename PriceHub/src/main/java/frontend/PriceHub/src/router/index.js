// router/index.js

import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../components/HomePage.vue'; // Adjust the path as needed
import SearchResults from '../components/Search.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomePage,
  },
  {
    path: '/search',
    name: 'search',
    component: SearchResults,
    props: (route) => ({ searchResults: getSearchResults(route) }), // Pass search results as props
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Dummy function to get search results (replace with your logic)
function getSearchResults(route) {
  const searchQuery = route.query.q;
  // Implement logic to fetch search results based on the search query
  // Return an array of results
  return [{ id: 1, name: 'Watch 1', price: '$100' }, { id: 2, name: 'Watch 2', price: '$150' }];
}

export default router;
