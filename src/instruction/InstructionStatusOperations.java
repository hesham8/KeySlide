package instruction;


/**
 * The defined operations for an instruction status. This interface defines state transitions.
 * Contains default implementations of state transitions.
 *
 * @author heshamsalman
 */
public interface InstructionStatusOperations {

    /**
     * Switches state to LEFT
     * @param instruction the instruction to switch
     * @return InstructionStatus.LEFT
     * @throws UnsupportedStatusTransitionException won't allow instructions to change states to themselves.
     */
    default InstructionStatus left(Instruction instruction) throws UnsupportedStatusTransitionException {
        return InstructionStatus.LEFT;
    }

    /**
     * Switches state to RIGHT
     * @param instruction the instruction to switch
     * @return InstructionStatus.RIGHT
     * @throws UnsupportedStatusTransitionException won't allow instructions to change states to themselves.
     */
    default InstructionStatus right(Instruction instruction) throws UnsupportedStatusTransitionException {
        return InstructionStatus.RIGHT;
    }

    /**
     * Switches state to UP
     * @param instruction the instruction to switch
     * @return InstructionStatus.UP
     * @throws UnsupportedStatusTransitionException won't allow instructions to change states to themselves.
     */
    default InstructionStatus up(Instruction instruction) throws UnsupportedStatusTransitionException {
        return InstructionStatus.UP;
    }

    /**
     * Switches state to DOWN
     * @param instruction the instruction to switch
     * @return InstructionStatus.DOWN
     * @throws UnsupportedStatusTransitionException won't allow instructions to change states to themselves.
     */
    default InstructionStatus down(Instruction instruction) throws UnsupportedStatusTransitionException {
        return InstructionStatus.DOWN;
    }

    /**
     * Switches state to STOP
     * @param instruction the instruction to switch
     * @return InstructionStatus.STOP
     * @throws UnsupportedStatusTransitionException won't allow instructions to change states to themselves. 
     */
    default InstructionStatus stop(Instruction instruction) throws UnsupportedStatusTransitionException {
        return InstructionStatus.STOP;
    }


}
