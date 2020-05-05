<template>
  <div class="about">
    <h1>About.json</h1>
    <ManageView/>
    <button class="buttonss" v-on:click="getAbout">Refresh</button>
    <div> {{results}} </div>
  </div>
</template>

<script>
import Config from "../components/config";
import ManageView from "./ManageView"
export default {
  name: "about.json",
  data() {
    return {
      results: []
    }
  },
  components: {
    ManageView
  },
  methods: {
    getAbout() {
      var responseCallback = (res) => {
      console.log("response", res);
    }
    var resultCallback = (result) => {
      this.results = result
    }
    Config.callServerApi(`about.json`, "GET", {}, resultCallback, responseCallback)
    }
  },
  mounted() {
    this.getAbout()
  }
}
</script>

<style>
.buttonss {
  margin-bottom: 3%;
  background-color:#033F63;
  color: #FFF;
  width: 100px;
  height: 40px;
  border-radius: 50px;
}
</style>