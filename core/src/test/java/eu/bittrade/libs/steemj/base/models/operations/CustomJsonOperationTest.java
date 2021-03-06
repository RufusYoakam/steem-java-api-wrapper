package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "custom json operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "1200010764657a3133333706666f6c6c6f77465b22666f6c6c6f77222c7b2"
            + "2666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c227768617422"
            + "3a5b22626c6f67225d7d5d";
    final String EXPECTED_TRANSACTION_HASH = "7ad5960642b2a3e8ad10813da8d985db0a2f0063f0fea212f753af02c99192de";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce9c80457011200010764657a3133333706666f6c6c6f77465b22666f6c6c6f77222c7b2266"
            + "6f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5"
            + "b22626c6f67225d7d5d00";

    private static CustomJsonOperation customJsonOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        // Steem does not allow to combine operations that require a posting key
        // with operations that require a higher key, so set the active
        // authorities to null.
        ArrayList<AccountName> requiredActiveAuths = null;

        String id = "follow";
        String json = "[\"follow\",{\"follower\":\"dez1337\",\"following\":\"steemj\",\"what\":[\"blog\"]}]";

        customJsonOperation = new CustomJsonOperation(requiredActiveAuths, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(customJsonOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
