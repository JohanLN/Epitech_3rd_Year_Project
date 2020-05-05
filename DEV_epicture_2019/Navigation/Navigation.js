import { createStackNavigator } from 'react-navigation-stack'
import { createAppContainer } from 'react-navigation'
import Search from '../Component/Search'
import UploadScreen from '../Component/UploadScreen'
import FavoriteScreen from '../Component/FavoriteScreen.js'
import Connect from '../Component/Connect'
import AuthWebView from '../API/AuthApi'
import UploadedImageScreen from '../Component/UploadedImageScreen'

const SearchStackNavigator = createStackNavigator({
  Search: {
    screen: Search
  },
  AuthWebView: {
    screen: AuthWebView
  },
  UploadScreen: {
    screen: UploadScreen
  },
  FavoriteScreen: {
      screen: FavoriteScreen
  },
  UploadedImageScreen: {
    screen: UploadedImageScreen
  }
})

export default createAppContainer(SearchStackNavigator)