package com.vtencon.quoteshake.ui.di

import com.vtencon.quoteshake.ui.data.newquotation.NewQuotationDataSource
import com.vtencon.quoteshake.ui.data.newquotation.NewQuotationDataSourceImpl
import com.vtencon.quoteshake.ui.newquotation.NewQuotationRepository
import com.vtencon.quoteshake.ui.newquotation.NewQuotationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module @InstallIn(SingletonComponent::class)
abstract class NewQuotationBinderModule {
    @Binds
    abstract fun bindNewQuotationRepository(impl: NewQuotationRepositoryImpl) : NewQuotationRepository
    @Binds
    abstract fun bindNewQuotationDataSource(impl: NewQuotationDataSourceImpl) : NewQuotationDataSource
}