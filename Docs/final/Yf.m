clear;
Ractual = 1.3;
Ra = [2.1 2.2 2.3 2.09 1.98 1.97 1.8 1.9 2.1 2.2];
Td = 1;
%Tf = [10 21 21 21 21 15 21 41 61 11 12];
b = 231 ; % Size of Token Bucket
f = 10;
Rd = [0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 ];
for k=1:10

    TDmax = max ( 1 / Ra(mod(k+1,10)+1), 1/Ractual);
    TDmin = min ( 1 / Ra(mod(k+1,10)+1), 1/Ractual);
    Rd(k) = 1 / ( Td * ( TDmax - TDmin) + TDmin);

        temp = 0;
        for fi = 1: (f-1)
            Tf = rand * 34;
             temp = temp + (Ractual- Rd(mod(fi,10)+1)) * Tf ; %(mod(k,10)+1);
        end 
        Y(k) = 1/b * temp;   
end
Y
plot(Y);
ylabel('Degree of Membership')
xlabel ('Y(f)')
grid
