import React from 'react'
import { StyleSheet, View, TextInput, Button, Text, FlatList, ActivityIndicator } from 'react-native'
import AuthWebView from '../API/AuthApi'

class Connect extends React.Component {
    constructor(props) {
      super(props)
    }

    _displayWeb = () => {
      this.props.navigation.navigate('AuthWebView')
    }

    render() {
        return (
          <View style={styles.main_container}>
            <Button 
              title='Connect' 
              onPress={() => this._displayWeb()}
            />      
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
    }
})

export default Connect