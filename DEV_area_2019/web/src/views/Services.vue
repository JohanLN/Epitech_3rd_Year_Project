<template>
  <div id="Services" >
    <h1>This is an Services page</h1>
    <ManageView/>
    <RegisterServices/>
  </div>
</template>

<script>

import ManageView from "./ManageView"
import RegisterServices from "./RegisterServices"

export default {
  data() {
    return {
    };
  },
  components: {
    ManageView,
    RegisterServices
  }
};
</script>

<style>
</style>