module f ( output s , input a, input b);
assign s = a & b;
endmodule //f
module test_f;
reg a;
reg b;
wire wire_a;
f module_test (wire_a, a, b);
initial
begin : main
$display("test module");
$display("    a    b =    wire_a ");
$monitor(" %4b %4b = %4b" , a, b, wire_a);
#1 a=1'b0; b=1'b0; 
#1 a=1'b0; b=1'b1; 
#1 a=1'b1; b=1'b1; 
#1 a=1'b1; b=1'b0; 
end
endmodule //test
