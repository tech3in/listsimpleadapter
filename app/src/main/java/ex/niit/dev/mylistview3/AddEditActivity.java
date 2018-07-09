package ex.niit.dev.mylistview3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditActivity extends AppCompatActivity {

    private EditText itemName;
    private EditText itemQty;
    private EditText itemPrice;
    private int index = Util.NEW_ITEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        itemName = (EditText)findViewById(R.id.itemNameEditText);
        itemQty = (EditText)findViewById(R.id.itemQtyEditText);
        itemPrice = (EditText)findViewById(R.id.itemPriceEditText);
        Button addEdit = (Button)findViewById(R.id.addEditBtn);
        addEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Util.ITEM_NAME,itemName.getText().toString());
                intent.putExtra(Util.ITEM_PRICE,itemPrice.getText().toString());
                intent.putExtra(Util.ITEM_QTY,itemQty.getText().toString());
                intent.putExtra(Util.INDEX,index);
                setResult(Util.RES_CODE,intent);
                //finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            index = bundle.getInt(Util.INDEX);
            if (index != Util.NEW_ITEM){
                //edit
                itemName.setText(bundle.getString(Util.ITEM_NAME));
                itemPrice.setText(bundle.getString(Util.ITEM_PRICE));
                itemQty.setText(bundle.getString(Util.ITEM_QTY));
            }
        }
    }
}
