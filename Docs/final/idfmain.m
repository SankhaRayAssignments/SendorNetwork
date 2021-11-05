
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

% End of Xf


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

%end of y(k)

for k=1:9

if (Y(k) >=0.0 && Y(k)<=0.2 )&& (X(k) >=0.0 && X(k)<=0.2) id(k) = 0.1;
    
    elseif (Y(k) >=0.0 && Y(k)<=0.2 ) && (X(k) >0.2 && X(k)<=0.5)  
        id(k) = 0.1;
        
    elseif (Y(k) >=0.0 && Y(k)<=0.2 )&& (X(k) >0.5 && X(k) <=0.8)   id(k) = 0.09;
        
    elseif (Y(k)>= 0.0 && Y(k)<=0.2 )&& (X(k) >0.8 && X(k) <=1.0)   id(k) = 0.7;
        
    elseif (Y(k) > 0.2 && Y(k)<= 0.5) && (X(k) >0.8 && X(k) <=1.0)   id(k) = 0.95;
        
    elseif (Y(k) >0.2 && Y(k)<=0.5 ) && (X(k) >0.2 && X(k)<=0.5 )   id(k) = 0.45;
        
    elseif (Y(k) >0.2 && Y(k) <=0.5 )&& (X(k) >0.5 && X(k) <=0.8)   id(k) = 0.3;
        
    elseif ((Y(k) >0.2 && Y(k)<=0.5 )&& (X(k) >0.8 && X(k) <=1.0))   id(k) = 0.09;
        
    elseif (Y(k) > 0.5 && Y(k) <= 1.0 )&& (X(k) >=0.0 && X(k) <=0.2)   id(k) = 0.95;
        
    elseif (Y(k) > 0.5 && Y(k) <= 1.0)&& (X(k) >0.2 && X(k)<=0.5)   id(k) = 0.3;
        
    elseif (Y(k) > 0.5 && Y(k) <= 1.0 )&& (X(k) >0.5 && X(k) <=0.8)   id(k) = 0.1;
        
    elseif (Y(k) > 0.5 && Y(k) <= 1.0 )&& (X(k) >0.8 && X(k) <=1.0)   id(k) = 0.09;
end
    
end

plot( id)
ylabel('Degree of Membership')
xlabel ('id(f)')

id
text(2.1,0.85,'Very-Large', 'FontSize', 12 );
text(2.1,0.65,'Large','FontSize', 12);
text(2.1,0.55,'Intermediate','FontSize', 12);
text(2.1,0.25,'Small','FontSize', 12);
text(2.1,0.15,'Very-Small','FontSize', 12);

grid;