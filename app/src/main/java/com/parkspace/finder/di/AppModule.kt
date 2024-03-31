package com.parkspace.finder.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.parkspace.finder.data.AuthRepository
import com.parkspace.finder.data.AuthRepositoryImpl
import com.parkspace.finder.data.BookingDetailRepository
import com.parkspace.finder.data.BookingDetailRepositoryImpl
import com.parkspace.finder.data.DataStoreRepository
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.ParkingSpaceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module for providing application-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
     * Provides an instance of FirebaseAuth.
     */
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Provides the implementation of AuthRepository.
     */
    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    /**
     * Provides an instance of FirebaseFirestore.
     */
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Provides the implementation of ParkingSpaceRepository.
     */
    @Provides
    fun provideParkingSpaceRepository(impl: ParkingSpaceRepositoryImpl): ParkingSpaceRepository = impl

    /**
     * Provides an instance of DataStoreRepository.
     */
    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository = DataStoreRepository(context)

    /**
     * Provides the implementation of BookingDetailRepository.
     */
    @Provides
    fun provideBookingDetailRepository(impl: BookingDetailRepositoryImpl): BookingDetailRepository = impl
}
