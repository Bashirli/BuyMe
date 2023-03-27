package com.bashirli.buyme.di

import android.content.Context
import androidx.room.Room
import com.bashirli.buyme.repo.roomrepo.cart.DatabaseRepo
import com.bashirli.buyme.repo.roomrepo.cart.DbRepo
import com.bashirli.buyme.repo.roomrepo.favorites.FavRepo
import com.bashirli.buyme.repo.roomrepo.favorites.FavoritesRepo
import com.bashirli.buyme.repo.roomrepo.location.LocRepo
import com.bashirli.buyme.repo.roomrepo.location.LocationRepo
import com.bashirli.buyme.service.room.cartdb.RoomDB
import com.bashirli.buyme.service.room.favoritesdb.FavoritesDAO
import com.bashirli.buyme.service.room.favoritesdb.FavoritesDB
import com.bashirli.buyme.service.room.locationdb.LocationDAO
import com.bashirli.buyme.service.room.locationdb.LocationDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDI {


    @Singleton
    @Provides
    fun injectDbRepo(roomDB: RoomDB)= DatabaseRepo(roomDB.getDao()) as DbRepo

    @Singleton
    @Provides
    fun injectRoomDB(@ApplicationContext context: Context)= Room.databaseBuilder(
        context, RoomDB::class.java,"myDatabase"
    ).build()

    @Singleton
    @Provides
    fun injectRoomDao(roomDB: RoomDB)=roomDB.getDao()


    @Singleton
    @Provides
    fun injectLocationDB(@ApplicationContext context:Context)=Room.databaseBuilder(
        context,LocationDB::class.java,"locationDB"
    ).build()

    @Singleton
    @Provides
    fun injectLocationDAO(locationDB: LocationDB)=locationDB.getDao()

    @Singleton
    @Provides
    fun injectRepo(locationDAO: LocationDAO)= LocRepo(locationDAO) as LocationRepo

    @Singleton
    @Provides
    fun injectFavoritesDB(@ApplicationContext context: Context)=Room.databaseBuilder(
        context,FavoritesDB::class.java,"favoritesdb"
    ).build()

    @Singleton
    @Provides
    fun injectFavoritesDAO(favoritesDB: FavoritesDB)=favoritesDB.getDao()

    @Singleton
    @Provides
    fun injectFavoritesRepo(favoritesDAO: FavoritesDAO)=FavRepo(favoritesDAO) as FavoritesRepo


}