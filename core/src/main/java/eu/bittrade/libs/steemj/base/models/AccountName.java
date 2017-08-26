package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.serializer.AccountNameSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem data type "account_name_type".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = AccountNameSerializer.class)
public class AccountName implements ByteTransformable {
    private String accountName;

    /**
     * Create an account name object with an empty account name.
     */
    public AccountName() {
        this.setAccountName("");
    }

    /**
     * Create an account name object containing the given account name.
     * 
     * @param accountName
     *            The account name.
     */
    public AccountName(String accountName) {
        this.setAccountName(accountName);
    }

    /**
     * Get the account name of this instance.
     * 
     * @return The account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Set the account name of this instance.
     * 
     * @param accountName
     *            The account name.
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName.toLowerCase();
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        return SteemJUtils.transformStringToVarIntByteArray(accountName);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherAccount) {
        if (this == otherAccount)
            return true;
        if (otherAccount == null || !(otherAccount instanceof AccountName))
            return false;
        AccountName other = (AccountName) otherAccount;
        return this.getAccountName().equals(other.getAccountName());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getAccountName() == null ? 0 : this.getAccountName().hashCode());
        return hashCode;
    }

    /**
     * Returns {@code true} if, and only if, the account name has more than
     * {@code 0} characters.
     *
     * @return {@code true} if the account name has more than {@code 0},
     *         otherwise {@code false}
     */
    public boolean isEmpty() {
        return this.getAccountName().isEmpty();
    }
}