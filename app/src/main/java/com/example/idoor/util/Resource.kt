package com.example.idoor.util

import retrofit2.HttpException
import java.io.IOException

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    companion object {
        fun <T> handleApiError(e: Exception): Resource.Error<T> {
            return when (e) {
                is IOException -> {
                    Resource.Error("Terjadi kesalahan jaringan: Tidak dapat terhubung ke internet. Pastikan koneksi internet Anda aktif.")
                }
                is HttpException -> {
                    if (e.code() == 404) {
                        Resource.Error("Data yang diproses tidak dapat ditemukan di server.")
                    } else {
                        Resource.Error("Error: ${e.message}")
                    }
                }
                else -> {
                    Resource.Error("Error: ${e.message}")
                }
            }
        }
    }
}