import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import About from '../views/About.vue'
import Client from '../views/Client.vue'
import Services from '../views/Services.vue'
import DiscordAuth from '../views/AuthServices/DiscordAuth.vue'
import FacebookAuth from '../views/AuthServices/FacebookAuth.vue'
import GithubAuth from '../views/AuthServices/GithubAuth.vue'
import GmailAuth from '../views/AuthServices/GmailAuth.vue'
import LinkedinAuth from '../views/AuthServices/LinkedinAuth.vue'
import TwitterAuth from '../views/AuthServices/TwitterAuth.vue'
import YoutubeAuth from '../views/AuthServices/YoutubeAuth.vue'
import ImgurAuth from '../views/AuthServices/ImgurAuth.vue'
import SlackAuth from '../views/AuthServices/SlackAuth.vue'
import store from '../store/index.js'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: {
        name: "login"
    }
  },
  {
    path: "/login",
    name: "login",
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/about.json',
    name: 'about',
    component: About,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/client.apk',
    name: 'client',
    component: Client,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/services',
    name: 'services',
    component: Services,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
        next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/facebookauth',
    name: 'facebookauth',
    component: FacebookAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/twitterauth',
    name: 'twitterauth',
    component: TwitterAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/discordauth',
    name: 'discordauth',
    component: DiscordAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/githubauth',
    name: 'githubauth',
    component: GithubAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/gmailauth',
    name: 'gmailauth',
    component: GmailAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/linkedinauth',
    name: 'linkedinauth',
    component: LinkedinAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/youtubeauth',
    name: 'youtubeauth',
    component: YoutubeAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/imgurauth',
    name: 'imgurauth',
    component: ImgurAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
  {
    path: '/slackauth',
    name: 'slackauth',
    component: SlackAuth,
    beforeEnter: (to, from, next) => {
      if(store.state.authenticated == false) {
          next(false);
        } else {
          next();
        }
    }
  },
]

  const router = new VueRouter({
    routes
  })

export default router
