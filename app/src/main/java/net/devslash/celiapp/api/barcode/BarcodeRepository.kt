package net.devslash.celiapp.api.barcode

import io.reactivex.Observable

interface Repository<T: Entity> {
    fun byId(id: String): Observable<T>
}
