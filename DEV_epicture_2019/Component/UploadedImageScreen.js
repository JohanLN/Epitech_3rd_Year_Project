import React from 'react'
import { StyleSheet, View, Text, FlatList, Button} from 'react-native'
import { displayUploadedImages } from '../API/Api'
import UploadedItems from './UploadedItems'

class UploadedImage extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            refresh: false,
            imageResult: [],
        }
      }

    _findMyImages() {
        this.state.refresh = true
        this.setState({
            imageResult: [],
        })
        displayUploadedImages().then(data => {
            this.setState({
                imageResult: this.state.imageResult.concat(data.data),
            })
        })
    }

    _noImages() {
        if (this.state.imageResult.length == 0 && this.state.refresh ==  true) {
            return (
                <Text style={styles.main_container}>No images</Text>
            )
        }
    }


    render() {
        return (
            <View style={styles.main_container}>
                <Button title='Refresh' onPress={() => this._findMyImages()}></Button>
                <FlatList
                    data={this.state.imageResult}
                    keyExtractor={(item) => item.id}
                    renderItem={({item}) => <UploadedItems up={item}></UploadedItems>}>
                </FlatList>
                {this._noImages()}
            </View>        )
    }
}

const styles = StyleSheet.create({

    main_container: {
        flex: 1,
        flexDirection: 'column',
    },
})

export default UploadedImage