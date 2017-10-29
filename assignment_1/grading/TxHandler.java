import java.util.ArrayList;

public class TxHandler {

    /* Creates a public ledger whose current UTXOPool (collection of unspent
     * transaction outputs) is utxoPool. This should make a defensive copy of
     * utxoPool by using the UTXOPool(UTXOPool uPool) constructor.
     */

    private UTXOPool utxoPool;

    public TxHandler(UTXOPool utxoPool) {
        this.utxoPool = new UTXOPool(utxoPool);
    }


    public boolean isValidTx(Transaction tx) {
        UTXOPool utxoPool = new UTXOPool();
        // (1) all outputs claimed by tx are in the current UTXO pool

        // (2) the signatures on each input of tx are valid,

        // (3) no UTXO is claimed multiple times by tx,

        // (4) all of tx’s output values are non-negative, and

        // (5) the sum of tx’s input values is greater than or equal to the sum of
        // its output values;

        return true; // placeholder
        // return true;
    }

    /* Handles each epoch by receiving an unordered array of proposed
     * transactions, checking each transaction for correctness,
     * returning a mutually valid array of accepted transactions,
     * and updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // instantiate something with .add()
        ArrayList<Transaction> validTxs = new ArrayList<Transaction>();

        for (Transaction t : possibleTxs) {
            if (isValidTx(t)) {
                validTxs.add(t);
                // remove the inputs from the transaction pool (destroy old coins)
                for (Transaction.Input input : t.getInputs()) utxoPool.removeUTXO(new UTXO(input.prevTxHash, input.outputIndex));

                // add outputs to the transaction pool (create new coins)
                for (int i = 0; i < t.numOutputs(); i++) {
                    Transaction.Output out = t.getOutput(i);
                    UTXO utxo = new UTXO(t.getHash(), i);
                    utxoPool.addUTXO(utxo, out);
                }
            }
        }

        // oh java
        Transaction[] validTxArray = new Transaction[validTxs.size()];
        return validTxs.toArray(validTxArray);
    }

}
