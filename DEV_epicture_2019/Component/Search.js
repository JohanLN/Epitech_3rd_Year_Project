import React from 'react'
import { StyleSheet, View, TextInput, Button, Text, FlatList, ActivityIndicator, ToastAndroid} from 'react-native'
import ImageItem from './ImageItem'
import { searchImage, imageUpload } from '../API/Api'

class Search extends React.Component {

  constructor(props) {
    super(props)
    this.searchedText = "",
    this.state = {
      imageResult: [],
      utils: [],
      isLoading: false
    }
  }

  _searchTextInputChanged(text) {
    this.searchedText = text
  }

  _searchImages () {
    this.setState({
      imageResult: []
    })
    if (this.searchedText.length > 0) {
      this.setState({isLoading: true})
      searchImage(this.searchedText).then(data => {
        if (data.data.total_items == 0) {
          this.setState({
            isLoading: false
          })
          ToastAndroid.show('No results for : ' + data.data.name, ToastAndroid.SHORT)
          return (0)
        }
        this.setState({
          imageResult: this.state.imageResult.concat(data.data.items),
          isLoading: false 
        })
      })
    }
  }

  _displayUploadScreen = () => {
    this.props.navigation.navigate('UploadScreen')
  }

  _displayFavoriteScreen = () => {
    this.props.navigation.navigate('FavoriteScreen')
  }

  _displayWeb = () => {
    this.props.navigation.navigate('AuthWebView')
  }

  _displayUploadedImage = () => {
    this.props.navigation.navigate('UploadedImageScreen')
  }

  _displayLoading () {
    if (this.state.isLoading) {
        return (
            <View style={styles.loading_container}>
                <ActivityIndicator size='large'></ActivityIndicator>
            </View>
        )
    }
}
  
  render() {
    return (
      <View style={styles.main_container}>
        <Button 
          title='Connect' 
          onPress={() => this._displayWeb()}
        />     
        <View> 
          <Text style={styles.header}>Epicture</Text>
        </View>
        <TextInput
          style={styles.textinput}
          placeholder='Search'
          onChangeText={(text) => this._searchTextInputChanged(text)}
          onSubmitEditing={() => this._searchImages()}
        />
        <Button 
          title='Search' 
          onPress={() => this._searchImages()}
        />        
        <FlatList
          data={this.state.imageResult}
          keyExtractor={(item) => item.id}
          renderItem={({item}) => <ImageItem img={item}></ImageItem>}>
        </FlatList>
        {this._displayLoading()}
        <View style={styles.account_task}>
          <Button title='Upload' onPress={() => this._displayUploadScreen()/*imageUpload()*/}/>
          <Button title='Favorite' onPress={() => this._displayFavoriteScreen()}/>
          <Button title='ImageUploaded' onPress={() => this._displayUploadedImage()}/>
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  main_container: {
    flex: 1
  },
  header: {
    color: '#aed8ea',
    textAlign: "center",
    fontSize: 50,
  },
  textinput: {
    marginLeft: 5,
    marginRight: 5,
    height: 50,
    borderColor: '#000000',
    borderWidth: 1,
    paddingLeft: 5
  },
  loading_container: {
    position: 'absolute',
    left: 0,
    right: 0,
    top: 100,
    bottom: 0,
    alignItems: 'center',
    justifyContent: 'center'
  },
})

export default Search