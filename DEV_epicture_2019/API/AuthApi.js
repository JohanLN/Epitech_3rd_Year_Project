import secrets from './ImgurToken'
import React, { Component } from 'react'
import { StyleSheet, View, TextInput, Button, Text, FlatList, ActivityIndicator } from 'react-native'
import { WebView } from 'react-native-webview'
import Connect from '../Component/Connect'
import Search from '../Component/Search'
import Compuser from '../Component/Container'
import { generateAccess } from './Api'


class AuthWebView extends Search {
  constructor(props) {
    super(props)
    this.state = {
      isLoading: false,
    }
  }

  _onNavigationStateChange = (event) => {
    let URLSplit = event.url.substr(event.url.indexOf("#") + 1, event.url.length)
    URLSplit = URLSplit.split("&")
    const UrlParse = []
    console.log("\n")
    for (let i = URLSplit.length-1; i >= 0; i--) {
      UrlParse.push(URLSplit[i].split("="))
    }
    if (UrlParse.length > 5) {
      Compuser.set(UrlParse)
    }
  }

  render() {
        return (
          <WebView 
          source={{uri: "https://api.imgur.com/oauth2/authorize?response_type=token&client_id=" + secrets.clientId}}
          onNavigationStateChange={this._onNavigationStateChange}
          />
        )
    }
}


export default AuthWebView