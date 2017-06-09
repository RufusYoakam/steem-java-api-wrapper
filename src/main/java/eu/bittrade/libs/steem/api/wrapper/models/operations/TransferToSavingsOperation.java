package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "transfer_to_savings_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToSavingsOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private Asset amount;
    private String memo;

    /**
     * Create a new transfer to savings operation to transfer an {@link #amount
     * amount} from the {@link #from from} account into the savings wallet of
     * the {@link #to to} account.
     */
    public TransferToSavingsOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the account name of the user who transfered the amount.
     * 
     * @return The user that transfered the amount.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account name of the user who wants to transfer the amount.
     * 
     * @param from
     *            The user that transfered the amount.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the user who received the transfered amount.
     * 
     * @return The user who received the transfered amount.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Get the user who should receive the amount.
     * 
     * @param to
     *            The user who should receive the amount.
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * Get the transfered amount.
     * 
     * @return The transfered amount.
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * Set the amount to transfer.
     * 
     * @param amount
     *            The amount to transfer.
     */
    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    /**
     * Get the message added to this operation.
     * 
     * @return The message added to this operation.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Add an additional message to this operation.
     * 
     * @param memo
     *            The message added to this operation.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferToSavingsOperation = new ByteArrayOutputStream()) {
            serializedTransferToSavingsOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.TRANSFER_TO_SAVINGS_OPERATION.ordinal()));
            serializedTransferToSavingsOperation.write(this.getFrom().toByteArray());
            serializedTransferToSavingsOperation.write(this.getTo().toByteArray());
            serializedTransferToSavingsOperation.write(this.getAmount().toByteArray());
            serializedTransferToSavingsOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getMemo()));

            return serializedTransferToSavingsOperation.toByteArray();
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
