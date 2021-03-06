package eu.bittrade.libs.steemj.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.NetworkParameters;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJConfigTest {
    private static final String STEEMJ_API_USERNAME = "customnodename";
    private static final String STEEMJ_API_PASSWORD = "test1234";
    private static final String STEEMJ_KEY_ACCOUNTNAME = "dez1337";
    private static final String STEEMJ_KEY_POSTING = "5JpbHHrEkoLsxNcddo5YaTgtmgDegTcjk8i7BDPiTbMefrPnjWK";
    private static final String STEEMJ_KEY_ACTIVE = "5J6a9B9H1rBC9XsxHUrv9Eu98cG4MaZPuaMk6LBfMSDGyk5SoiP";
    private static final String STEEMJ_KEY_OWNER = "5JhxZZ6oGwFm2egPWyy21DWvroSoUur33sEHBamobDdSmhPN9U4";
    private static final String STEEMJ_KEY_MEMO = "5Hw3qRsC3f9yLtVazZpA8LyCUozBJq5aQv9tNNnz8fcg8BqoAWw";

    @BeforeClass
    public static void setUp() {
        System.setProperty("steemj.api.username", STEEMJ_API_USERNAME);
        System.setProperty("steemj.api.password", STEEMJ_API_PASSWORD);
        System.setProperty("steemj.key.accountName", STEEMJ_KEY_ACCOUNTNAME);
        System.setProperty("steemj.key.posting", STEEMJ_KEY_POSTING);
        System.setProperty("steemj.key.active", STEEMJ_KEY_ACTIVE);
        System.setProperty("steemj.key.owner", STEEMJ_KEY_OWNER);
        System.setProperty("steemj.key.memo", STEEMJ_KEY_MEMO);

        // As there may have been other tests earlier we need to create a new
        // SteemJConfig instance so the parameter above will take effect.
        SteemJConfig.getNewInstance();
    }

    @Test
    public void testSettingsThroughSystemProperties() {
        assertThat(SteemJConfig.getInstance().getApiUsername(), equalTo(new AccountName(STEEMJ_API_USERNAME)));
        assertThat(String.valueOf(SteemJConfig.getInstance().getApiPassword()), equalTo(STEEMJ_API_PASSWORD));

        AccountName accountName = SteemJConfig.getInstance().getPrivateKeyStorage().getAccounts().get(0);
        assertThat(accountName, equalTo(new AccountName(STEEMJ_KEY_ACCOUNTNAME)));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage()
                .getKeyForAccount(PrivateKeyType.POSTING, accountName).decompress()
                .getPrivateKeyEncoded(NetworkParameters.fromID(NetworkParameters.ID_MAINNET)).toBase58(),
                equalTo(STEEMJ_KEY_POSTING));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage()
                .getKeyForAccount(PrivateKeyType.ACTIVE, accountName).decompress()
                .getPrivateKeyEncoded(NetworkParameters.fromID(NetworkParameters.ID_MAINNET)).toBase58(),
                equalTo(STEEMJ_KEY_ACTIVE));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.OWNER, accountName)
                .decompress().getPrivateKeyEncoded(NetworkParameters.fromID(NetworkParameters.ID_MAINNET)).toBase58(),
                equalTo(STEEMJ_KEY_OWNER));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.MEMO, accountName)
                .decompress().getPrivateKeyEncoded(NetworkParameters.fromID(NetworkParameters.ID_MAINNET)).toBase58(),
                equalTo(STEEMJ_KEY_MEMO));
    }
}
