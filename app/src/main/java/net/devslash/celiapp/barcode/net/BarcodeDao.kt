package net.devslash.celiapp.barcode.net

import io.reactivex.Observable
import net.devslash.celiapp.api.barcode.BarcodeResult
import retrofit2.http.GET
import retrofit2.http.Path

interface BarcodeDao {

    @GET("barcode/{id}")
    fun getForBarcode(@Path("id") barcodeId: String): Observable<BarcodeResult>

}