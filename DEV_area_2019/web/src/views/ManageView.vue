<template>  
  <div id="ManageView" class="pages">
    <b-nav pills>
      <b-nav-item-dropdown
        id="my-nav-dropdown"
        text="Navigation"
        toggle-class="nav-link-custom"
        right
      >
        <b-dropdown-item href="/#/services">Services</b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item href="/#/about.json">About.json</b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item href="/#/client.apk">Client.apk</b-dropdown-item>
      </b-nav-item-dropdown>
      <button class="buttons" v-on:click="Logout">Log out</button>
    </b-nav>
  </div>
</template>

<script>
import Config from "../components/config"
export default {
  name: 'ManageView',
  data() {
    return {

    };
  },
  methods: {
    Logout() {
      var myHeaders = new Headers();
      myHeaders.append('Content-Type', 'application/json');
      var responseCallback = (res) => {
        console.log(res);
        if (res.status === 201) {
          this.$store.commit("setAuthentication", true);
          this.$router.replace({ name: "services" });
        } else
          alert("The username and/or password is incorrect")
      };
      var resultCallback = (result) => {
        console.log("connect", result);
      };         
      Config.callServerApi(`api/Users/logout`, "GET", {}, resultCallback, responseCallback);
      this.$router.replace({name: 'login'})
    }
  }

}
</script>

<style>
.buttons {
  margin-left: 80%;
  background-color:#033F63;
  color: #FFF;
  width: 100px;
  height: 40px;
  border-radius: 50px;
}
.pages {
  margin-bottom: 2%;
}
</style>>
