package de.skyslycer.skylocalizer.exceptions

class UnhandledReaderTypeException(override val message: String?, override val cause: Throwable?) : Throwable() {
    constructor(message: String?) : this(message, null)

    constructor(cause: Throwable?) : this(cause?.toString(), cause)

    constructor() : this(null, null)
}