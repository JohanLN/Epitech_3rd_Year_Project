<template>
  <div id="RegisterServices">
     <table class="table table-striped">
      <thead class="thead-dark">
        <tr>
          <th scope="col">Service à connecter</th>
          <th scope="col">Service</th>
          <th scope="col">Ajout Services</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">Slack</th>
          <td>Connection Slack</td>
          <td>
            <button class="buttnss" v-on:click="redirectSlack">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Imgur</th>
          <td>Connection Imgur</td>
          <td>
            <button class="buttnss" v-on:click="redirectImgur">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Twitter</th>
          <td>Connection Twitter</td>
          <td>
            <button class="buttnss" v-on:click="redirectTwitter">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Facebook</th>
          <td>Connection Facebook</td>
          <td>
            <button class="buttnss" v-on:click="redirectFacebook">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Gmail</th>
          <td>Connect Gmail</td>
          <td>
            <button class="buttnss" v-on:click="redirectGmail">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Youtube</th>
          <td>Connection Youtube</td>
          <td>
            <button class="buttnss" v-on:click="redirectYoutube">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Github</th>
          <td>Connection Github</td>
          <td>
            <button class="buttnss" v-on:click="redirectGithub">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Linkedin</th>
          <td>Connection Linkedin</td>
          <td>
            <button class="buttnss" v-on:click="redirectLinkedin">Connect services</button>
          </td>
        </tr>
      </tbody>
      <tbody>
        <tr>
          <th scope="row">Discord</th>
          <td>Connection Discord</td>
          <td>
            <button class="buttnss" v-on:click="redirectDiscord">Connect services</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'RegisterServices',
  data() {
      return {     
      };
  },
  methods: {
    redirectYoutube() {
      this.$router.replace({name: 'youtubeauth'})
    },
    redirectFacebook() {
      this.$router.replace({name: 'facebookauth'})
    },
    redirectTwitter() {
      this.$router.replace({name: 'twitterauth'})
    },
    redirectDiscord() {
      this.$router.replace({name: 'discordauth'})
    },
    redirectGithub() {
      this.$router.replace({name: 'githubauth'})
    },
    redirectGmail() {
      this.$router.replace({name: 'gmailauth'})
    },
    redirectLinkedin() {
      this.$router.replace({name: 'linkedinauth'})
    },
    redirectImgur() {
      this.$router.replace({name: 'imgurauth'})
    },
    redirectSlack() {
      this.$router.replace({name: 'slackauth'})
    }
  }
};
</script>

<style>
.buttnss {
  margin-bottom: 3%;
  background-color:#033F63;
  color: #FFF;
  width: 150px;
  height: 40px;
  border-radius: 50px;
}
</style>