export class Server {
    _id: string;
    name: string;
    version: string;
    envoyName: string;
    lastUptime: string;

    // Private
    loading: number = 0;
    isOnline: number = 0;
}