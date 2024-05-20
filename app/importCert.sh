mydir=/tmp/certs
truststore=${mydir}/rds-truststore.jks
storepassword=123456

# Certifique-se de que o diretório de certificados exista
mkdir -p ${mydir}

# Faça o download do arquivo global-bundle.pem
wget "https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem" -O ${mydir}/global-bundle.pem

# Verifique se o download foi bem-sucedido
if [ $? -eq 0 ]; then
    # Use awk para separar o arquivo global-bundle.pem em certificados individuais
    awk -v RS="-----BEGIN CERTIFICATE-----" 'NF > 1 {print "-----BEGIN CERTIFICATE-----" $0}' ${mydir}/global-bundle.pem > ${mydir}/rds-ca-

    for CERT in ${mydir}/rds-ca-*; do
      alias=$(openssl x509 -noout -text -in $CERT | perl -ne 'next unless /Subject:/; s/.*(CN=|CN = )//; print')
      echo "Importing $alias"
      keytool -import -file ${CERT} -alias "${alias}" -storepass ${storepassword} -keystore ${truststore} -noprompt
      rm $CERT
    done

    rm ${mydir}/global-bundle.pem

    echo "Trust store content is:"

    keytool -list -v -keystore "$truststore" -storepass ${storepassword} | grep Alias | cut -d " " -f3- | while read alias
    do
       expiry=`keytool -list -v -keystore "$truststore" -storepass ${storepassword} -alias "${alias}" | grep Valid | perl -ne 'if(/until: (.*?)\n/) { print "$1\n"; }'`
       echo " Certificate ${alias} expires in '$expiry'"
    done
else
    echo "Download of global-bundle.pem failed."
fi
