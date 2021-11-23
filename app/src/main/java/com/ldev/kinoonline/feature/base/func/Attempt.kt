package com.ldev.kinoonline.feature.base

import com.ldev.kinoonline.feature.base.func.Either

inline fun <reified T> attempt(func: () -> T): Either<Throwable, T> = try {
    Either.Right(func.invoke())
} catch (e: Throwable) {
    Either.Left(e)
}