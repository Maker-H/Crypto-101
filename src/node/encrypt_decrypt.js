import {webcrypto} from "node:crypto";

const pemHF = {
    public: {
        header: '-----BEGIN PUBLIC KEY-----',
        footer: '-----END PUBLIC KEY-----',
    },
    private: {
        header: '-----BEGIN PRIVATE KEY-----',
        footer: '-----END PRIVATE KEY-----',
    },
};

const arrayBufferToStr = (buf) => {
    return String.fromCharCode.apply(null, new Uint8Array(buf));
};

const strToArrayBuffer = (str) => {
    const encoder = new TextEncoder();
    return encoder.encode(str).buffer;
};

const encryptRSA = async (text, publicKeyb64) => {
    const binaryDerString = atob(publicKeyb64.replace(pemHF.public.footer, '').replace(pemHF.public.header, ''));
    const binaryDer = strToArrayBuffer(binaryDerString);
    const publicKey = await webcrypto.subtle.importKey(
        'spki',
        binaryDer,
        {
            name: 'RSA-OAEP',
            hash: 'SHA-256',
        },
        true,
        ['encrypt']
    );
    const cipher = await webcrypto.subtle.encrypt(
        {
            name: 'RSA-OAEP',
        },
        publicKey,
        strToArrayBuffer(text)
    );
    return window.btoa(arrayBufferToStr(cipher));
};


const decryptRSA = async (cipher, privateKeyb64) => {
    const binaryDerString = window.atob(privateKeyb64.replace(pemHF.private.footer, '').replace(pemHF.private.header, ''));
    const binaryDer = strToArrayBuffer(binaryDerString);
    const privateKey = await window.crypto.subtle.importKey(
        'pkcs8',
        binaryDer,
        {
            name: 'RSA-OAEP',
            hash: 'SHA-256',
        },
        true,
        ['decrypt']
    );
    const text = await window.crypto.subtle.decrypt(
        {
            name: 'RSA-OAEP',
        },
        privateKey,
        strToArrayBuffer(window.atob(cipher))
    );
    return arrayBufferToStr(text);
};