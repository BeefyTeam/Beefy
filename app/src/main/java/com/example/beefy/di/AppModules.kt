package com.example.beefy.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.source.local.UserPreference
import com.example.beefy.data.source.remote.ApiConfig
import com.example.beefy.ui.auth.forgotpasswordscreen.ForgotPasswordViewModel
import com.example.beefy.ui.auth.loginscreen.LoginScreenViewModel
import com.example.beefy.ui.auth.registerbuyerscreen.RegisterBuyerViewModel
import com.example.beefy.ui.auth.registersellerscreen.RegisterSellerViewModel
import com.example.beefy.ui.auth.splashscreen.SplashScreenViewModel
import com.example.beefy.ui.buyer.BuyerChatsListScreen.BuyerChatsListViewModel
import com.example.beefy.ui.buyer.buyerchatscreen.BuyerChatViewModel
import com.example.beefy.ui.buyer.buyercheckoutscreen.BuyerCheckoutViewModel
import com.example.beefy.ui.buyer.buyereditprofilescreen.BuyerEditProfileViewModel
import com.example.beefy.ui.buyer.buyerhomescreen.BuyerHomeScreenViewModel
import com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailcomplete.BuyerOrderDetailCompleteViewModel
import com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailonprocess.BuyerOrderDetailOnProcessViewModel
import com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailpaid.BuyerOrderDetailPaidViewModel
import com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailunpaid.BuyerOrderDetailUnpaidViewModel
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete.BuyerOrderStatusCompleteViewModel
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessViewModel
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuspaid.BuyerOrderStatusPaidViewModel
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuswaitingpayment.BuyerOrderStatusUnpaidViewModel
import com.example.beefy.ui.buyer.buyerproductdetailscreen.BuyerProductDetailViewModel
import com.example.beefy.ui.buyer.buyerprofilescreen.BuyerProfileViewModel
import com.example.beefy.ui.buyer.buyerscanhistoryscreen.BuyerScanHistoryViewModel
import com.example.beefy.ui.buyer.buyerscanscreen.BuyerScanViewModel
import com.example.beefy.ui.buyer.buyersearchscreen.BuyerSearchViewModel
import com.example.beefy.ui.buyer.buyerstoredetailscreen.BuyerStoreDetailViewModel
import com.example.beefy.ui.buyer.buyeruploadpaymentproofscreen.BuyerUploadPaymentProofViewModel
import com.example.beefy.ui.seller.selleradditemscreen.SellerAddItemViewModel
import com.example.beefy.ui.seller.sellerchatslistscreen.SellerChatsListViewModel
import com.example.beefy.ui.seller.sellerdetailitemscreen.SellerDetailItemViewModel
import com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailcompletetransactionscreen.SellerDetailCompleteTransactionViewModel
import com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailprocesstransactionscreen.SellerDetailProcessTransactionViewModel
import com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailwaitingtransactionscreen.SellerDetailWaitingTransactionViewModel
import com.example.beefy.ui.seller.selleredititemscreen.SellerEditItemViewModel
import com.example.beefy.ui.seller.sellereditprofilescreen.SellerEditProfileViewModel
import com.example.beefy.ui.seller.sellerhomescreen.SellerHomeViewModel
import com.example.beefy.ui.seller.sellerprofilescreen.SellerProfileViewModel
import com.example.beefy.ui.seller.sellertransactionscreen.sellercompletetransactionscreen.SellerCompleteTransactionViewModel
import com.example.beefy.ui.seller.sellertransactionscreen.sellerprocesstransactionscreen.SellerProcessTransactionViewModel
import com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen.SellerWaitingTransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val Context.dataStore by preferencesDataStore(name = "user_preferences")


val apiModule = module {
    single { ApiConfig(androidContext().dataStore).getApiService() }
}

val userPreferenceModule = module {
    single { UserPreference(androidContext().dataStore) }
}

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { BuyerRepository(get()) }
    single { SellerRepository(get()) }
    single { UserPrefRepository(get()) }

}

val viewModelModule = module {
    //auth
    viewModel { SplashScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { RegisterBuyerViewModel(get(),get()) }
    viewModel { RegisterSellerViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }

    //buyer
    viewModel { BuyerHomeScreenViewModel(get()) }
    viewModel { BuyerProfileViewModel(get(),get()) }
    viewModel { BuyerChatsListViewModel(get(),get())}
    viewModel { BuyerCheckoutViewModel(get(), get(), get())}
    viewModel { BuyerOrderDetailPaidViewModel(get())}
    viewModel { BuyerOrderDetailUnpaidViewModel(get(),get())}
    viewModel { BuyerOrderDetailOnProcessViewModel(get())}
    viewModel { BuyerOrderDetailCompleteViewModel(get())}
    viewModel { BuyerOrderStatusOnProcessViewModel(get(),get())}
    viewModel { BuyerOrderStatusCompleteViewModel(get(),get())}
    viewModel { BuyerOrderStatusUnpaidViewModel(get(),get())}
    viewModel { BuyerOrderStatusPaidViewModel(get(),get())}
    viewModel { BuyerProductDetailViewModel(get(), get())}
    viewModel { BuyerScanHistoryViewModel(get(), get())}
    viewModel { BuyerScanViewModel(get(),get())}
    viewModel { BuyerStoreDetailViewModel(get(), get()) }
    viewModel { BuyerUploadPaymentProofViewModel(get()) }
    viewModel { BuyerSearchViewModel(get()) }
    viewModel { BuyerChatViewModel(get()) }
    viewModel { BuyerEditProfileViewModel(get(), get()) }


    //seller
    viewModel { SellerChatsListViewModel(get(),get()) }
    viewModel { SellerAddItemViewModel(get(),get()) }
    viewModel { SellerDetailItemViewModel(get()) }
    viewModel { SellerDetailCompleteTransactionViewModel(get(),get(),get()) }
    viewModel { SellerDetailProcessTransactionViewModel(get(),get(),get()) }
    viewModel { SellerDetailWaitingTransactionViewModel(get(), get(),get()) }
    viewModel { SellerEditItemViewModel(get()) }
    viewModel { SellerEditProfileViewModel(get(), get()) }
    viewModel { SellerHomeViewModel(get(),get()) }
    viewModel { SellerProfileViewModel(get(),get()) }
    viewModel { SellerCompleteTransactionViewModel(get(),get()) }
    viewModel { SellerProcessTransactionViewModel(get(),get()) }
    viewModel { SellerWaitingTransactionViewModel(get(),get()) }

}