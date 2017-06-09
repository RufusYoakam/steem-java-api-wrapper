package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "cancel_transfer_from_savings_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CancelTransferFromSavingsOperation extends Operation {
    private AccountName from;
    // Original type is uint32_t so we use long here;
    @JsonProperty("request_id")
    private long requestId;

    /**
     * Create a new cancel transfer from savings operation. Use this operation
     * to cancel a
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation}.
     */
    public CancelTransferFromSavingsOperation() {
        super(PrivateKeyType.POSTING);
        // Set default values.
        this.setRequestId(0);
    }

    /**
     * Get the account whose
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} has been canceled.
     * 
     * @return The account whose transfer to savings operation has been
     *         canceled.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account whose
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} should be canceled.
     * 
     * @param from
     *            The account whose transfer to savings operation should be
     *            canceled.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the id of the canceled
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation}.
     * 
     * @return The id of the canceled TransferFromSavingsOperation.
     */
    public int getRequestId() {
        return (int) requestId;
    }

    /**
     * Set the id of the
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} to cancel.
     * 
     * @param requestId
     *            The id of the canceled TransferFromSavingsOperation.
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCancelTransferFromSavingsOperation = new ByteArrayOutputStream()) {
            serializedCancelTransferFromSavingsOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.CANCEL_TRANSFER_FROM_SAVINGS_OPERATION.ordinal()));
            serializedCancelTransferFromSavingsOperation.write(this.getFrom().toByteArray());
            serializedCancelTransferFromSavingsOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getRequestId()));

            return serializedCancelTransferFromSavingsOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
