package org.yarokovisty.delivery.libs.encryption

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.PosixFilePermission
import java.security.SecureRandom

actual fun createPlatformEncryptionKeyProvider(context: Any?): EncryptionKeyProvider =
    JVMFileBasedEncryptionKeyProvider()

private class JVMFileBasedEncryptionKeyProvider : EncryptionKeyProvider {
    private val mutex = Mutex()
    private var cachedKey: ByteArray? = null

    override suspend fun provideKey(): ByteArray {
        return cachedKey ?: mutex.withLock {
            cachedKey ?: getOrCreateKeyBytes().also { cachedKey = it }
        }
    }

    private fun getOrCreateKeyBytes(): ByteArray {
        val keyFile = getKeyFile()

        if (keyFile.exists()) {
            return keyFile.readBytes()
        }

        val newKey = ByteArray(KEY_SIZE_BYTES)
        SecureRandom().nextBytes(newKey)

        // Use atomic write to prevent TOCTOU race
        val path = keyFile.toPath()
        Files.write(
            path,
            newKey,
            StandardOpenOption.CREATE_NEW,
            StandardOpenOption.WRITE,
        )
        setFilePermissions(keyFile)

        return newKey
    }

    private fun getKeyFile(): File {
        val configDir = when {
            System.getProperty("os.name").lowercase().contains("win") -> {
                val appData = System.getenv("APPDATA") ?: System.getProperty("user.home")
                File(appData, "delivery")
            }
            else -> {
                val homeDir = System.getProperty("user.home")
                File(homeDir, ".config/delivery")
            }
        }
        configDir.mkdirs()
        return File(configDir, KEY_FILE_NAME)
    }

    @Suppress("SwallowedException")
    private fun setFilePermissions(file: File) {
        try {
            val path = file.toPath()
            val permissions = setOf(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
            )
            Files.setPosixFilePermissions(path, permissions)
        } catch (e: UnsupportedOperationException) {
            // Windows doesn't support POSIX permissions, silently ignore
        }
    }

    private companion object {
        const val KEY_FILE_NAME = "encryption_key"
        const val KEY_SIZE_BYTES = 32
    }
}
