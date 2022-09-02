package de.tngbl.parsley.onlinewardleymaps;

/**
 * Hints are used to signal the need for a postprocessing operation for a certain aspect.
 * Postprocessing is done by a {@link InterpreterHintStrategy} instance created by the Hint.
 */
public interface InterpreterHint {

    InterpreterHintStrategy getStrategy();

}
