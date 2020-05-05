import React from 'react'
import { StyleSheet, View, Text, Image, Button, FlatList } from 'react-native'
import favorite from '../Helpers/FavoriteHelp'
import FavoriteItem from './FavoriteItem'
import { findFavorite } from '../API/Api'
import Search from './Search'

class Favorites extends Search {

    constructor(props) {
        super(props)
        this.state = {
            refresh: false,
            imageResult: [],
        }
      }

    _findFavorite() {
        this.state.refresh = true
        this.setState({
            imageResult: [],
        })
        findFavorite(this.state.userName).then(data => {
            this.setState({
                imageResult: this.state.imageResult.concat(data.data),
            })
        })
    }
    _noFavorite() {
        if (this.state.imageResult.length == 0 && this.state.refresh ==  true) {
            return (
                <Text style={styles.main_container}>No favorite</Text>
            )
        }
    }

    render() {
        return (
            <View style={styles.main_container}>
                <Button title='Refresh' onPress={() => this._findFavorite()}></Button>
                <FlatList
                    data={this.state.imageResult}
                    keyExtractor={(item) => item.id}
                    renderItem={({item}) => <FavoriteItem fav={item}></FavoriteItem>}>
                </FlatList>
                {this._noFavorite()}
            </View>
        )
    }
}

const styles = StyleSheet.create({

    main_container: {
        flex: 1,
        flexDirection: 'column',
    },
})

export default Favorites