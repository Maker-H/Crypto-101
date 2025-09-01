import {webcrypto} from "node:crypto";
import { writeFile } from "node:fs/promises";

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

/*
keyType
public -> spki
private -> pkcs8
 */
const exportKey = async (keyType, key) => {
    const exportedRaw = await webcrypto.subtle.exportKey(keyType === 'public' ? 'spki' : 'pkcs8', key);
    console.log(`[exportedRaw]${keyType}: `, exportedRaw);

    const exportedAsBase64 = Buffer.from(exportedRaw).toString("base64");

    return pemHF[keyType].header + '\n' + exportedAsBase64 + '\n' + pemHF[keyType].footer;
};

const createKeyPair = async () => {
    const keyPair = await webcrypto.subtle.generateKey(
        {
            name: 'RSA-OAEP',
            modulusLength: 2048,
            publicExponent: new Uint8Array([1, 0, 1]),
            hash: 'SHA-256',
        },
        true,
        ['encrypt', 'decrypt']
    );
    const publicKey = await exportKey('public', keyPair.publicKey);
    const privateKey = await exportKey('private', keyPair.privateKey);
    return {publicKey: publicKey, privateKey: privateKey};
};

const keyPair = await createKeyPair();

console.log('publicKey: ', keyPair.publicKey);
console.log('privateKey: ', keyPair.privateKey);

await ("../../cert/public.raw", keyPair.publicKey, { mode: 0o644 })
await writeFile("../../cert/private.raw", keyPair.privateKey, { mode: 0o644 })