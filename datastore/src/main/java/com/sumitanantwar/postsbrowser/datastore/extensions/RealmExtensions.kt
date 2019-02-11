package com.sumitanantwar.postsbrowser.datastore.extensions

import android.os.Looper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm

fun Realm.executeTrasaction(transaction: (Realm) -> Unit) : Single<Boolean> {
    return Single.just(true)
        .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        .flatMap {_ ->
            Single.create<Boolean> { emitter ->
                executeTransactionAsync(transaction, {
                    emitter.onSuccess(true)
                }, {
                    emitter.onError(it)
                } )
            }
        }
}