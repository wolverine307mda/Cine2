package org.example.cache

interface Cache<K, T> {
    fun get(key: K): Result<T>
    fun put(key: K, value: T): Result<Unit>
    fun remove(key: K): Result<Unit>
    fun clear(): Result<Unit>
}
