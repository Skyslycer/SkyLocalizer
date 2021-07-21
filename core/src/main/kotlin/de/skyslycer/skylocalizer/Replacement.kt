package de.skyslycer.skylocalizer

/**
 * A replacement for a string
 *
 * @param placeholder The placeholder text (No placeholder prefix or suffix)
 * @param replacement The replacement that should replace the placeholder
 *
 * @see SkyLocalizer.get
 * @see SkyLocalizer.replace
 */
class Replacement(val placeholder: String, val replacement: String)