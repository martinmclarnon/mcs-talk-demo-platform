<template>
  <div class="book">
    <h2>{{ book.title }}</h2>
    <p><strong>Author:</strong> {{ book.author }}</p>
    <p><strong>ISBN:</strong> {{ book.isbn }}</p>
    <p><strong>Publisher:</strong> {{ book.publisher }}</p>
    <p><strong>Published Date:</strong> {{ book.publishedDate }}</p>
    <p><strong>Number of Pages:</strong> {{ book.numberOfPages }}</p>
    <p><strong>Language Written In:</strong> {{ book.languageWrittenIn }}</p>
    <p><strong>Review:</strong> {{ book.review }}</p>
  </div>
</template>

<script>
export default {
  props: {
    book: Object
  }
}
</script>

<style scoped>
.book {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
</style>
