<template>
  <div class="about">
    <h1>Client.apk</h1>
    <ManageView/>
    <a class="btn btn-lg btn-primary" href="../clientapk/client.apk" download>Download APK</a>
  </div>
</template>

<script>
import Config from "../components/config";
import ManageView from "./ManageView"
export default {
  name: "client.apk",
  data() {
    return {
    }
  },
  components: {
    ManageView
  },
  methods: {
  }
}
</script>

<style>

</style>