
clear all;
Ractual = 0.1;
Ra = [2.1 2.2 2.3 2.09 1.98 1.97 1.8 1.9 2.1 2.2];
Td = [1 1 1 1 1 1 1 1 1 1];
Tf = [.001 .021 .021 .211 .021 .001 .0021 .0041 .061 .011];
f = 10;
Rd = [0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 0.1 ];


for k=1:9
    Ra(k) = rand+2;
    TDmax = max ( 1 / Ra(k+1), 1/Ractual);
    TDmin = min ( 1 / Ra(k+1), 1/Ractual);
    Rd(k) = 1 / ( Td(k) * ( TDmax - TDmin) + TDmin);
end
Ra
for k=1:9
    for ki = 0:9
        temp=0;
        for fi = 1: (f-1)
           % Tf = rand/11;
             temp = temp + (Ra(fi) - Rd(fi))*Tf(fi);
        end 
        X(10-k) = 1/k * temp;
    end
end
X
plot(X);
ylabel('Degree of Membership')
xlabel ('X(f)')
grid
