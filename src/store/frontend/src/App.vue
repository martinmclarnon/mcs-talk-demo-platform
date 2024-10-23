<template>
  <div id="app">
    <h1>Books</h1>
    <div v-if="loading">Loading...</div>
    <div v-else>
      <Book v-for="book in books" :key="book.id" :book="book" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Book from './components/BookComponent.vue';

const BOOKS_BASE_URL = process.env.VUE_APP_BASE_URL;
const BOOKS_ENDPOINT = process.env.VUE_APP_BOOKS_ENDPOINT;
const booklist = `${BOOKS_BASE_URL}${BOOKS_ENDPOINT}`;

console.log(booklist);

export default {
  name: 'App',
  components: {
    Book
  },
  data() {
    return {
      books: [],
      loading: true
    };
  },
  async mounted() {
    try {
      const response = await axios.get(booklist);
      this.books = response.data.data;
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      this.loading = false;
    }
  }
}
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  margin-top: 20px;
}
</style>
